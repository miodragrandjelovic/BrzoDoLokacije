using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.DTOComponents;
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
        [Authorize(Roles = "Admin")]
        [HttpGet("GetaAvailableRoles/{username}")]
        public async Task<IActionResult> GetaAvailableRoles(string username)
        {
            var availableRoles = userService.GetAvailableRolesForUser(username);
            return Ok(availableRoles);
        }


        [HttpGet("GetUser")]
        public async Task<IActionResult> GetUser()
        {
            var user = userService.GetUser();
            if(user == null)
                return NotFound();
            return Ok(user);
        }

        [HttpPut("UpdateUser")]
        public async Task<IActionResult> UpdateUserCredentials(UserDTO user)
        {
            var succeed = userService.UpdateUser(user);

            var message = new { message = succeed ? "Uspesno azurirani podaci!" : "Greska pri azuriranju podataka!" };
            if(!succeed)
                return BadRequest(message);
            return Ok(message);
        }



    }
}
