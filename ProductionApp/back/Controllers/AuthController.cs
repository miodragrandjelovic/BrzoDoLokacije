using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.JWTManager.Interfaces;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.DTOComponents;
using System.Text.Json.Serialization;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IUserService userService;
        private readonly IEncryptionManager encryptionManager;
        private readonly IJWTManagerRepository jwtManager;

        public AuthController(IUserService userService, IEncryptionManager encryptionManager, IJWTManagerRepository jWTManager)
        {
            this.userService = userService;
            this.encryptionManager = encryptionManager;
            this.jwtManager = jWTManager;
        }

        [AllowAnonymous]
        [HttpPost("register")]
        public async Task<IActionResult> Regitser(RegisterDTO request)
        {
            Console.WriteLine(request.Username + " " + request.Password);
            byte[] passwordHash, passwordKey;
            if (await userService.UserAlreadyExists(request.Username))
                return BadRequest("Vec postoji korisnik sa unetim nickom");

            encryptionManager.EncryptPassword(request.Password, out passwordHash, out passwordKey);

            User newUser = new User
            {
                Username = request.Username,
                Password = passwordHash,
                PasswordKey = passwordKey,
                Email = request.Email,
                Role = userService.GetUserRole(),
                CountryId = Constants.ModelConstants.DEFAULT

            };
            userService.AddNewUser(newUser);

            return Ok(new {
                message = "Uspesno registrovan korisnik " + newUser.Username
            });
        }

        


        [AllowAnonymous]
        [HttpPost("login")]
        public async Task<IActionResult> Login(LoginDTO request)
        {

            var user = userService.GetUser(request.UsernameOrEmail);
            if (user == null)
                return NotFound("Korisnik ne postoji");

            if (!encryptionManager.DecryptPassword(request.Password, user.Password, user.PasswordKey))
                return BadRequest("Pogresna lozinka");


            var tokenString = jwtManager.GenerateToken(user);
            
            return Ok(
                new{
                    token = tokenString
            });
        }

    }
}
