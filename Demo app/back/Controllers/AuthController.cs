using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.JWTManager.Interfaces;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services.Interfaces;

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
                CountryId = 1

            };
            userService.AddNewUser(newUser);

            return Ok("uspesno registrovan korisnik " + newUser.Username);
        }


        [AllowAnonymous]
        [HttpPost("login")]
        public async Task<ActionResult<string>> Login(LoginDTO request)
        {

            var user = userService.GetUser(request.Username);
            if (user == null)
                return Unauthorized("Korisnik ne postoji");

            if (!encryptionManager.DecryptPassword(request.Password, user.Password, user.PasswordKey))
                return Unauthorized("Pogresna lozinka");


            var token = jwtManager.GenerateToken(user);
            return Ok(token);
        }

        [Authorize]
        [HttpGet("test")]
        public async Task<ActionResult<string>> Test()
        {
            var loggedUser = userService.GetLoggedUser();
            if (string.IsNullOrEmpty(loggedUser))
                return Unauthorized("Korisnik nije prijavljen na sistem");

            return Ok(loggedUser);
        }


    }
}
