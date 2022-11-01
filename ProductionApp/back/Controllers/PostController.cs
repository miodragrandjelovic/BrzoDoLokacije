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
    public class PostController : ControllerBase
    {
        private readonly IUserService userService;
        private readonly IPostService postService;

        public PostController(IUserService userService, IPostService postService)
        {
            this.userService = userService;
            this.postService = postService;
        }
        [HttpPost("NewPost")]
        public async Task<IActionResult> CreatePost(NewPostDTO post)
        {
            postService.AddPost(post);
            return Ok(); 
        }
        [HttpGet("SetLike/{id}")]
        public async Task<IActionResult> SetLikeOnPost(int id)
        {
            postService.SetLikeOnPost(id);

            return Ok();

        }
        [Authorize(Roles ="Admin")]
        [HttpDelete("DeleteUserPost/{postId}")]
        public async Task<IActionResult> DeleteUserPost(int postId, string userName)
        {
            bool succeed = postService.DeleteUserPost(postId, userName);

            var answer = new { message = succeed ? "Uspesno obrisan post!" : "Greska pri brisanju posta!" };
            if(!succeed)
                return BadRequest(answer);
            return Ok(answer);
        }
        
        [HttpDelete("DeletePost/{postId}")]
        public async Task<IActionResult> DeletePost(int postId)
        {
            postService.DeletePost(postId);
            return Ok();
        }
        [HttpGet("GetUserPosts/{username}")]
        public async Task<IActionResult> GetUserPosts(string username)
        {
            var posts = postService.GetUserPosts(username);
            return Ok(posts);
        }
    }
}
