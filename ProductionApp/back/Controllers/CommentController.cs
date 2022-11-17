using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.Controllers
{
    [Authorize(Roles = "User,Admin")]
    [Route("api/[controller]")]
    [ApiController]
    public class CommentController : ControllerBase
    {
        private readonly ICommentUI commentUI;

        public CommentController(ICommentUI commentUI)
        {
            this.commentUI = commentUI;
        }


        [HttpGet("GetComment/{id}")]
        public async Task<IActionResult> GetCommentByCommentId(int id)
        {
            var answer = commentUI.GetComment(id);
            if(answer == null)
                return NotFound();
            return Ok(answer);
        }

        [HttpGet("GetCommentsForPost/{postId}")]
        public async Task<IActionResult> GetCommentsForPostByPostId(int postId)
        {
            var posts = commentUI.GetCommentsPost(postId);
            if (posts == null)
                return BadRequest();

            return Ok(posts);
        }

        [HttpPost("AddNewCommentOnPost")]
        public async Task<IActionResult> AddNewCommentOnPost(NewCommentDTO newComment)
        {
            var answer = commentUI.AddComment(newComment);
            if (answer.Message.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new {message = answer.Message});
        }

        [HttpDelete("DeleteCommentFromPost/{id}")]
        public async Task<IActionResult> DeleteCommentFromPost(int commentId)
        {
            var answer = commentUI.DeleteComment(commentId);
            if (answer.Message.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new { message = answer.Message });
        }


    }
}
