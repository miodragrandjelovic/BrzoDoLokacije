using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Server.Kestrel.Core;
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

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<Database>(options =>
{
    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString));
});

// Configure Authentication

builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options => options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuerSigningKey = true,
        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(builder.Configuration.GetSection("AppSettings:Token").Value)),
        ValidateIssuer = false,
        ValidateAudience = false,
        ClockSkew = TimeSpan.Zero

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
builder.Services.AddTransient<IUserService, UserService>();
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
}

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseAuthentication();

app.UseAuthorization();

app.MapControllers();

app.Run();
