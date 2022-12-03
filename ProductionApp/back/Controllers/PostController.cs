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
        public async Task<IActionResult> CreatePost([FromForm] NewPostDTO post)
        {   //izmena da vraca response
            postUI.AddPost(post);
            return Ok(
                new
                {
                    message = "Uspesno dodat novi post"
                }
            );
        }
        [HttpPut("SetLike/{postId}")]
        public async Task<IActionResult> SetLikeOnPost(int postId)
        {
            var response = postUI.SetLikeOnPost(postId);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);

            return BadRequest(message);

        }
        [HttpDelete("RemoveLike/{postId}")]
        public async Task<IActionResult> RemoveLikeFromPost(int postId)
        {
            var response = postUI.RemoveLikeFromPost(postId);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);
            return BadRequest(message);
        }
        [HttpDelete("DeleteUserPost/{postId}")]
        public async Task<IActionResult> DeleteUserPost(int postId)
        {
            var response = postUI.DeleteUserPost(postId);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);
            return BadRequest(message);
        }
        [Authorize(Roles = "Admin")]
        [HttpDelete("DeletePost/{postId}")]
        public async Task<IActionResult> DeletePost(int postId)
        {
            var response = postUI.DeletePost(postId);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status500InternalServerError))
                return BadRequest(message);
            return Ok(message);
        }
        [HttpGet("GetUserPosts/{username}")]
        public async Task<IActionResult> GetUserPosts(string username)
        {
            var response = postUI.GetUserPosts(username);
            var message = new { message = response.Message };

            if (response.StatusCode.Equals(StatusCodes.Status500InternalServerError))
                return BadRequest(message);

            return Ok(response.Data.Cast<PostDTO>().ToList());
        }

        [HttpGet("GetPostById/{id}")]
        public async Task<IActionResult> GetPostById(int id)
        {
            var post = postUI.GetPost(id);
            if (post == null)
                return BadRequest(new { message = "Error" });
            return Ok(post);
        }

        [HttpGet("GetAllPosts/{sortType}")]
        public async Task<IActionResult> GetAllPosts(int sortType = 0)
        {
            var posts = postUI.GetAllPosts((SortType)sortType);
            return Ok(posts);
        }

        [HttpGet("GetFollowingPosts/{sortType}")]
        public async Task<IActionResult> GetFollowingPosts(int sortType = 0)
        {
            var posts = postUI.GetFollowingPosts((SortType)sortType);
            return Ok(posts);
        }

        [HttpGet("GetRecommendedPosts/{sortType}")]
        public async Task<IActionResult> GetRecommendedPosts(int sortType = 0)
        {
            var posts = postUI.GetRecommendedPosts((SortType)sortType);
            return Ok(posts);
        }

        [HttpGet("GetUsersPostOnMap/{username}")]
        public async Task<IActionResult> GetPostsOnMap(string username)
        {
            var response = postUI.GetPostsOnMap(username);
            var message = new { message = response.Message };

            if (response.StatusCode.Equals(StatusCodes.Status500InternalServerError))
                return BadRequest(message);

            return Ok(response.Data.Cast<PostOnMapDTO>().ToList());

        }
    }
}
