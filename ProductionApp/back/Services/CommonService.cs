using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services
{
    public static class ResponseService
    {
        public static Response GetResponse(bool succeed, string statusMessageSuccess, string statusMessageError)
        {
            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status500InternalServerError,
                    Message = statusMessageError
                };

            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = statusMessageSuccess
            };
        }
    }
}
