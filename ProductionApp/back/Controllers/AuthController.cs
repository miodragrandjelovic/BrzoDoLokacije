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
        private readonly IAuthService authService;

        public AuthController(IAuthService authService)
        {
            this.authService = authService;
        }

        [AllowAnonymous]
        [HttpPost("register")]
        public async Task<IActionResult> Regitser(RegisterDTO request)
        {
            var answer = authService.Register(request).Result;
            var message = new {message = answer.Message};
            if (answer.StatusCode.Equals(StatusCodes.Status400BadRequest))
                return BadRequest(message);

            return Ok(message);
        }

        


        [AllowAnonymous]
        [HttpPost("login")]
        public async Task<IActionResult> Login(LoginDTO request)
        {
            var answer = authService.Login(request).Result;
            var message = new { message = answer.Message };

            if (answer.StatusCode.Equals(StatusCodes.Status404NotFound))
                return NotFound(message);
            if (answer.StatusCode.Equals(StatusCodes.Status403Forbidden))
                return BadRequest(message);

            return Ok(message);

        }

    }
}
