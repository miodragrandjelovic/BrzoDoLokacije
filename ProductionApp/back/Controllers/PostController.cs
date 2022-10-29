using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Authorize(Roles = "user,admin")]
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

        [HttpGet("SetLike/{id}")]
        public async Task<IActionResult> SetLikeOnPost(int id)
        {
            postService.SetLikeOnPost(id);

            return Ok();

        }

    }
}
