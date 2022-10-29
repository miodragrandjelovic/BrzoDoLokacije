using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Authorize(Roles = "User,Admin")]
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserService userService;

        public UserController(IUserService userService)
        {
            this.userService = userService;
        }

        [Authorize(Roles ="Admin")]
        [HttpGet("GetAllUsers")]
        public async Task<IActionResult> GetAllUsers()
        { 
           return null;
        }


        [HttpGet("test")]
        public async Task<IActionResult> Test()
        {
            var loggedUser = userService.GetLoggedUser();
            if (string.IsNullOrEmpty(loggedUser))
                return Unauthorized("Korisnik nije prijavljen na sistem");

            return Ok(new
            {
                message = loggedUser
            });
        }

    }
}
