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

        [Authorize(Roles ="Admin")]
        [HttpPut("UpdateUserRole/{username}")]
        public async Task<IActionResult> UpdateRole(string username,string role)
        {
            var succeed = userService.UpdateUserRole(username,role);
            var answer = new { message = succeed ? "Uspesno azurirani podaci!" : "Greska pri azuriranju podataka!" };
            if (!succeed)
                return BadRequest(answer);
            return Ok(answer);
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
