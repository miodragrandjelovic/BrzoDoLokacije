using Microsoft.AspNetCore.Server.Kestrel.Core;
using PyxisKapriBack.DAL;
using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.JWTManager;
using PyxisKapriBack.JWTManager.Interfaces;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services;
using PyxisKapriBack.Services.Interfaces;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<Database>(options =>
{
    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString));
});

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Configure dependency injection

#region 'Interface - Class Dependency Injection'
builder.Services.AddTransient<IUserDAL, UserDAL>();
builder.Services.AddTransient<IPostDAL, PostDAL>();
#endregion

builder.Services.AddTransient<IUserService, UserService>();
builder.Services.AddTransient<IEncryptionManager, EncryptionManager>();
builder.Services.AddTransient<IJWTManagerRepository, JWTManagerRepository>();

builder.Services.Configure<KestrelServerOptions>(builder.Configuration.GetSection("Kestrel"));
var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
