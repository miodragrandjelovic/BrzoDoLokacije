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
        [Authorize(Roles ="Admin")]
        [HttpDelete("DeleteUserPost/{postId}")]
        public async Task<IActionResult> DeleteUserPost(int postId, string userName)
        {
            bool succeed = postUI.DeleteUserPost(postId, userName);

            var answer = new { message = succeed ? "Uspesno obrisan post!" : "Greska pri brisanju posta!" };
            if(!succeed)
                return BadRequest(answer);
            return Ok(answer);
        }
        
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
