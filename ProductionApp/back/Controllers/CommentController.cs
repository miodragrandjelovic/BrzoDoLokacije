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
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new {message = answer.Message});
        }

        [HttpDelete("DeleteCommentFromPost/{id}")]
        public async Task<IActionResult> DeleteCommentFromPost(int commentId)
        {
            var answer = commentUI.DeleteComment(commentId);
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new { message = answer.Message });
        }

        [HttpPut("SetLike/{commentId}")]
        public async Task<IActionResult> SetLikeOnComment(int commentId)
        {
            var response = commentUI.AddLike(commentId);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);

            return BadRequest(message);

        }

        [HttpPut("SetDislike/{commentId}")]
        public async Task<IActionResult> SetDislikeOnComment(int commentId)
        {
            var response = commentUI.AddDislike(commentId);
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);

            return BadRequest(message);

        }

        [HttpDelete("DeleteLikeFromComment/{id}")]
        public async Task<IActionResult> DeleteLikeFromComment(int commentId)
        {
            var answer = commentUI.DeleteLike(commentId);
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new { message = answer.Message });
        }

        [HttpDelete("DeleteDislikeFromComment/{id}")]
        public async Task<IActionResult> DeleteDislikeFromComment(int commentId)
        {
            var answer = commentUI.DeleteDislike(commentId);
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new { message = answer.Message });
        }

        [HttpGet("GetUsersWhoLikedComment/{commentId}")]
        public async Task<IActionResult> GetUsersWhoLikedComment(int commentId)
        {
            var users = commentUI.GetUsersWhoLiked(commentId);
            return Ok(users);
        }

        [HttpGet("GetUsersWhoDislikedComment/{commentId}")]
        public async Task<IActionResult> GetUsersWhoDislikedComment(int commentId)
        {
            var users = commentUI.GetUsersWhoDisliked(commentId);
            return Ok(users);
        }
    }
}
