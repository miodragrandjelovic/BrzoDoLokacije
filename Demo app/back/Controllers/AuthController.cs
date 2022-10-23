using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IUserService _userService;

        public AuthController(IUserService userService)
        {
            _userService = userService;
        }

        [AllowAnonymous]
        [HttpPost("register")]
        public async Task<IActionResult> Regitser(RegisterDTO request)
        {
            if (await _userService.UserAlreadyExists(request.Username))
                return BadRequest("Vec postoji korisnik sa unetim nickom");

            

            User newUser = new User
            {
                Username = request.Username,
                Password = request.Password,
                Email = request.Email

            };
            _userService.AddNewUser(newUser);

            return Ok("uspesno registrovan korisnik " + newUser.Username);
        }


        [AllowAnonymous]
        [HttpPost("login")]
        public async Task<ActionResult<string>> Login(LoginDTO request)
        {

            var user = _userService.GetUser(request.Username);
            if (user == null)
                return Unauthorized("Korisnik ne postoji");

            if (!user.Password.Equals(request.Password))
                return Unauthorized("Pogresna lozinka");


            
            return Ok("Uspesno logovan korisnik");
        }
    }
}
