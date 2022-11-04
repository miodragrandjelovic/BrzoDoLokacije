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
using PyxisKapriBack.LocationManager.Interfaces;

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

// Add services to the container.

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
builder.Services.AddTransient<IUserDAL, UserDAL>();
builder.Services.AddTransient<IPostDAL, PostDAL>();
builder.Services.AddTransient<IRoleDAL, RoleDAL>();
builder.Services.AddTransient<ILikeDAL, LikeDAL>();
builder.Services.AddTransient<IImageDAL, ImageDAL>();

builder.Services.AddTransient<ICountryDAL, CountryDAL>();
builder.Services.AddTransient<ICityDAL, CityDAL>();
builder.Services.AddTransient<ILocationDAL, LocationDAL>();

builder.Services.AddTransient<IAuthService, AuthService>();
builder.Services.AddTransient<IFileService, FileService>();
builder.Services.AddTransient<ILocationManager, LocationManager>(); 
builder.Services.AddTransient<IUserService, UserService>();
builder.Services.AddTransient<IPostService, PostService>();
builder.Services.AddTransient<ILikeService, LikeService>();
builder.Services.AddTransient<IPlaceService, PlaceService>(); 
builder.Services.AddTransient<IEncryptionManager, EncryptionManager>();
builder.Services.AddTransient<IJWTManagerRepository, JWTManagerRepository>();
builder.Services.AddTransient<IHttpContextAccessor,HttpContextAccessor>();
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

app.MapControllers();

app.Run();
