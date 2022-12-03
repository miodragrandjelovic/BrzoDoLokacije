using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PyxisKapriBack.PythonService;

namespace PyxisKapriBack.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TestController : ControllerBase
    {
        private readonly ServiceClient client;

        public TestController(ServiceClient client)
        {
            this.client = client;
        }



        [HttpGet]
        public async Task<IActionResult> Get()
        {
            var response = client.GetMean().Result;
            var message = new
            {
                message = response
            };
            return Ok(message);
        }
    }
}
