using Microsoft.AspNetCore.Authentication.JwtBearer;
using PyxisKapriBack.Models;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using PyxisKapriBack.DAL;
using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.JWTManager;
using PyxisKapriBack.JWTManager.Interfaces;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services;
using PyxisKapriBack.Services.Interfaces;
using Swashbuckle.AspNetCore.Filters;
using System.Text;
using PyxisKapriBack.LocationManager;
using PyxisKapriBack.UI.Interfaces;
using PyxisKapriBack.UI;
using Microsoft.AspNetCore.Server.Kestrel.Core;
using PyxisKapriBack.LocationManager.Interfaces;
using Microsoft.Extensions.FileProviders;
using PyxisKapriBack.PythonService;
using PyxisKapriBack.Chat;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<Database>(options =>
{
    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString));
});
// CORS Configuration 
builder.Services.AddCors(options =>
    options.AddDefaultPolicy(builder =>
    {
        builder.WithOrigins("https://localhost:7231")
        .AllowAnyHeader()
        .AllowAnyMethod();
    }
));

// Configure Authentication

builder.Services.AddAuthentication(item =>
{
    item.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    item.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
    .AddJwtBearer(options => {
        options.RequireHttpsMetadata = true;
        options.SaveToken = true;
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(builder.Configuration.GetSection("AppSettings:Token").Value)),
            ValidateIssuer = false,
            ValidateAudience = false,
            ClockSkew = TimeSpan.Zero

        }; 
    });

// Add PythonHttpClient

builder.Services.AddHttpClient<ServiceClient>();

// Add SignalR
builder.Services.AddSignalR();

// Add services to the container.
builder.Services.Configure<KestrelServerOptions>(builder.Configuration.GetSection("Kestrel"));
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options => {
    options.AddSecurityDefinition("oauth2", new OpenApiSecurityScheme
    {
        In = ParameterLocation.Header,
        Name = "Authorization",
        Type = SecuritySchemeType.ApiKey
    });
    options.OperationFilter<SecurityRequirementsOperationFilter>();
});

// Configure dependency injection

#region 'Interface - Class Dependency Injection'

    #region 'DAL Dependencies'
    builder.Services.AddTransient<IUserDAL, UserDAL>();
    builder.Services.AddTransient<IPostDAL, PostDAL>();
    builder.Services.AddTransient<IRoleDAL, RoleDAL>();
    builder.Services.AddTransient<ILikeDAL, LikeDAL>();
    builder.Services.AddTransient<IImageDAL, ImageDAL>();

    builder.Services.AddTransient<ICountryDAL, CountryDAL>();
    builder.Services.AddTransient<ICityDAL, CityDAL>();
    builder.Services.AddTransient<ILocationDAL, LocationDAL>();
    builder.Services.AddTransient<ICommentDAL, CommentDAL>();
    builder.Services.AddTransient<IDislikeDAL, DislikeDAL>();
    builder.Services.AddTransient<IFollowDAL, FollowDAL>();

    builder.Services.AddTransient< IMessageDAL, MessageDAL>();
    builder.Services.AddTransient<ICommentLikeDAL, CommentLikeDAL>();
    builder.Services.AddTransient<ICommentDislikeDAL, CommentDislikeDAL>();
#endregion

    #region 'Services - BL Dependencies'
    builder.Services.AddTransient<IAuthService, AuthService>();
    builder.Services.AddTransient<IFileService, FileService>();
    builder.Services.AddTransient<IUserService, UserService>();
    builder.Services.AddTransient<IPostService, PostService>();
    builder.Services.AddTransient<ILikeService, LikeService>();
    builder.Services.AddTransient<IPlaceService, PlaceService>();
    builder.Services.AddTransient<IFollowService, FollowService>();
    builder.Services.AddTransient<ICommentService, CommentService>();
    builder.Services.AddTransient<IMessageService, MessageService>();
    builder.Services.AddTransient<ICommentLikeService, CommentLikeService>();
    builder.Services.AddTransient<ICommentDislikeService, CommentDislikeService>();
    #endregion

    #region 'UI - Dependencies'
    builder.Services.AddTransient<IUserUI, UserUI>();
    builder.Services.AddTransient<IPostUI, PostUI>();
    builder.Services.AddTransient<IFollowUI, FollowUI>(); 
    builder.Services.AddTransient<ICommentUI, CommentUI>();
    builder.Services.AddTransient<IPlaceUI, PlaceUI>();
    builder.Services.AddTransient<IMessageUI, MessageUI>();
    #endregion

    #region 'Managers'
    builder.Services.AddTransient<ILocationManager, LocationManager>(); 
    builder.Services.AddTransient<IEncryptionManager, EncryptionManager>();
    builder.Services.AddTransient<IJWTManagerRepository, JWTManagerRepository>();
    builder.Services.AddTransient<IHttpContextAccessor, HttpContextAccessor>();
    #endregion

#endregion



var app = builder.Build();

/* prilikom pokretanja programa, automatski ce se update-ovati baza*/
using (var scope = app.Services.CreateScope())
{
    var context = scope.ServiceProvider.GetRequiredService<Database>();
    context.Database.Migrate();
    context.InsertDefaultValues(); 
}

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseAuthentication();

app.UseAuthorization();

app.UseCors();

app.MapHub<ChatHub>("/chat");

app.MapControllers();

app.UseStaticFiles(new StaticFileOptions
{
    FileProvider = new PhysicalFileProvider(
           Path.Combine(builder.Environment.ContentRootPath, "Images")),
    RequestPath = "/Images"
});

if (!File.Exists(Path.Combine(Directory.GetCurrentDirectory(), Constants.ROOT_FOLDER, "Temp")))
{
    Directory.CreateDirectory(Path.Combine(Directory.GetCurrentDirectory(), Constants.ROOT_FOLDER, "Temp"));
}

app.Run();
