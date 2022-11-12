﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Authorize(Roles = "User,Admin")]
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserUI userUI;
        private readonly IFollowUI followUI;
        public UserController(IUserUI userUI, IFollowUI followUI)
        {
            this.userUI   = userUI;
            this.followUI = followUI;
        }

        [Authorize(Roles ="Admin")]
        [HttpGet("GetAllUsers")]
        public async Task<IActionResult> GetAllUsers()
        { 
           var allUsers = userUI.GetAllUsers();
           return Ok(allUsers);
        }

        [Authorize(Roles ="Admin")]
        [HttpPut("UpdateUserRole/{username}")]
        public async Task<IActionResult> UpdateRole(string username,string role)
        {
            var answer = userUI.UpdateUserRole(username,role);
            if (answer.StatusCode.Equals(StatusCodes.Status400BadRequest))
                return BadRequest(new { message = answer.Message });
            return Ok(new { message = answer.Message });
        }
        [Authorize(Roles = "Admin")]
        [HttpGet("GetaAvailableRoles/{username}")]
        public async Task<IActionResult> GetaAvailableRoles(string username)
        {
            var availableRoles = userUI.GetAvailableRolesForUser(username);
            return Ok(availableRoles);
        }


        [HttpGet("GetUser")]
        public async Task<IActionResult> GetUser()
        {
            var user = userUI.GetUser();
            if(user == null)
                return NotFound();
            return Ok(user);
        }

        [HttpPut("UpdateUser")]
        public async Task<IActionResult> UpdateUserCredentials(UserDTO user)
        {
            var answer = userUI.UpdateUser(user);
            if(answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { token = answer.Message });
            return BadRequest(new { message = answer.Message });
        }

        [HttpPut("ChangePassword")]
        public async Task<IActionResult> ChangeUserPassword(CredentialsDTO credentials)
        {
            var answer = userUI.ChangeUserPassword(credentials);
            var message = new {message = answer.Message};
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);
            return BadRequest(message);
        }

        [HttpGet("GetFollowers")]
        public async Task<IActionResult> GetFollowers()
        {
            var followers = followUI.GetFollowers();
            return Ok(followers);
        }

        [HttpGet("GetFollowing")]
        public async Task<IActionResult> GetFollowing()
        {
            var following = followUI.GetFollowing();
            return Ok(following);
        }

        [HttpPost("AddFollow")]
        public async Task<IActionResult> AddFollow(string followingUsername)
        {   //izmena da vraca response
            var answer = followUI.AddFollow(followingUsername);
            var message = answer.Message;

            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);
            return BadRequest(message);
        }

        [HttpDelete("RemoveFollow/{followingUsername}")]
        public async Task<IActionResult> RemoveFollow(string followingUsername)
        {
            var response = followUI.DeleteFollow(followingUsername);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);
            return BadRequest(message);
        }
    }
}
