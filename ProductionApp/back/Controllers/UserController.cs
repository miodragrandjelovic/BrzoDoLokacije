using Microsoft.AspNetCore.Authorization;
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
        private readonly IFileService fileService;

        public UserController(IUserUI userUI, IFollowUI followUI, IFileService fileService)
        {
            this.userUI   = userUI;
            this.followUI = followUI;
            this.fileService = fileService;
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

        [HttpGet("GetUserByUsername/{username}")]
        public async Task<IActionResult> GetUser(string username)
        {
            var user = userUI.GetUser(username);
            if (user == null)
                return NotFound();
            return Ok(user);
        }

        [HttpPut("UpdateUser")]
        public async Task<IActionResult> UpdateUserCredentials([FromForm]UserDTO user)
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
        public async Task<IActionResult> AddFollow(FollowUsernameDTO followingUsername)
        {   //izmena da vraca response
            var answer = followUI.AddFollow(followingUsername.Username);
            var message = new { message = answer.Message };

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

        [HttpGet("IsFollowed/{followingUsername}")]
        public async Task<IActionResult> IsFollowed(string followingUsername)
        {
            var response = followUI.IsFollowed(followingUsername);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);
            return BadRequest(message);
        }

        [HttpPost("Test")]
        public async Task<IActionResult> Test(IFormFile image)
        {
            var path = @"C:\Users\Tekalo\Desktop\brzodolokacije\ProductionApp\back\Images\Tekalo";

            fileService.AddFile(path, image);

            return Ok("Proslo");
        }

        [HttpGet("GetFile")]
        public async Task<IActionResult> GetFile()
        {
            var user = userUI.GetLoggedUser();
            var file = fileService.GetFile(user, "profileImage.jpg");
            if (file == null)
                return BadRequest();

            return Ok(file);
        }

        [HttpPost("GetFile")]
        public async Task<IActionResult> GetFile(IFormFile image)
        {
           
            return Ok(image);
        }

        [HttpGet("GetProfileImage")]
        public async Task<IActionResult> GetProfileImage()
        {
            var path = @"C:\Users\Tekalo\Desktop\brzodolokacije\ProductionApp\back\Images\Tekalo";
            var image = fileService.GetUserProfileImage(path);

            return Ok(Convert.ToBase64String(image));
        }
    }
}
