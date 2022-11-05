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
    public class PostController : ControllerBase
    {
        private readonly IUserUI userUI;
        private readonly IPostUI postUI;

        public PostController(IUserUI userUI, IPostUI postUI)
        {
            this.userUI = userUI;
            this.postUI = postUI;
        }
        [HttpPost("NewPost")]
        public async Task<IActionResult> CreatePost(NewPostDTO post)
        {
            postUI.AddPost(post);
            return Ok(
                new{
                    message = "Uspesno dodat novi post"
                }
            );
        }
        [HttpGet("SetLike/{id}")]
        public async Task<IActionResult> SetLikeOnPost(int id)
        {
            postUI.SetLikeOnPost(id);

            return Ok(
                new
                {
                    message = "Uspesno postavljen like"
                }
            );

        }
        
        [HttpDelete("DeleteUserPost/{postId}")]
        public async Task<IActionResult> DeleteUserPost(int postId)
        {
            var response = postUI.DeleteUserPost(postId);

            if(response.StatusCode.Equals(StatusCodes.Status404NotFound))
                return NotFound(response);
            if (response.StatusCode.Equals(StatusCodes.Status403Forbidden))
                return BadRequest(response.Message);
            return Ok(response);
        }
        [Authorize(Roles = "Admin")]
        [HttpDelete("DeletePost/{postId}")]
        public async Task<IActionResult> DeletePost(int postId)
        {
            postUI.DeletePost(postId);
            return Ok(
                new
                {
                    message = "Uspesno obrisan post"
                }
            );
        }
        [HttpGet("GetUserPosts/{username}")]
        public async Task<IActionResult> GetUserPosts(string username)
        {
            var posts = postUI.GetUserPosts(username);
            return Ok(posts);
        }
    }
}
