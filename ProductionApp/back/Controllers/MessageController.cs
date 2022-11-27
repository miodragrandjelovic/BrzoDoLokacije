using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.UI.Interfaces;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace PyxisKapriBack.Controllers
{
    [Route("api/[controller]")]
    [Authorize(Roles = "User,Admin")]
    [ApiController]
    public class MessageController : ControllerBase
    {
        // GET api/<MessageController>/5
        private readonly IMessageUI messageUI; 

        public MessageController(IMessageUI messageUI)
        {
            this.messageUI = messageUI;
        }

        [HttpGet("GetMessage/{id}")]
        public async Task<IActionResult> GetMessageById(int id)
        {
            var answer = messageUI.GetMessage(id);
            if (answer == null)
                return NotFound();
            return Ok(answer);
        }

        [HttpPost("AddMessage")]
        public async Task<IActionResult> AddMessage(MessageDTO message)
        {
            var answer = messageUI.AddMessage(message);
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new { message = answer.Message });
        }

        [HttpDelete("DeleteMessage/{id}")]
        public async Task<IActionResult> DeleteMessage(int messageId)
        {
            var answer = messageUI.DeleteMessage(messageId);
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(new { message = answer.Message });

            return BadRequest(new { message = answer.Message });
        }

        [HttpPut("UpdateMessage")]
        public async Task<IActionResult> UpdateMessage(MessageDTO updateMessage)
        {
            var response = messageUI.UpdateMessage(updateMessage); 
            var message = new { message = response.Message };
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                return Ok(message);

            return BadRequest(message);
        }

        [HttpGet("GetChat/{usernameReceiver}")]
        public async Task<IActionResult> GetChat(String usernameReceiver)
        {
            var messages = messageUI.GetMessages(usernameReceiver);
            if (messages == null)
                return NotFound(); 

            return Ok(messages);
        }
    }
}
