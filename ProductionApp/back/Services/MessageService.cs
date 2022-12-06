using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class MessageService : IMessageService
    {
        private readonly IMessageDAL iMessageDAL;

        public MessageService(IMessageDAL iMessageDAL)
        {
            this.iMessageDAL = iMessageDAL;
        }

        public Response AddMessage(Message message)
        {
            try
            {
                bool result = iMessageDAL.AddMessage(message);
                return ResponseService.CreateOkResponse(result.ToString()); 
            }
            catch(Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public Response DeleteMessage(int messageId)
        {
            try
            {
                bool result = iMessageDAL.DeleteMessage(messageId);
                return ResponseService.CreateOkResponse(result.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public Message GetMessage(int messageId)
        {
            return iMessageDAL.GetMessage(messageId);
        }

        public List<Message> GetMessages(string usernameSender, string usernameReceiver)
        {
            return iMessageDAL.GetMessages(usernameSender, usernameReceiver);
        }

        public Response UpdateMessage(Message message)
        {
            try
            {
                bool result = iMessageDAL.UpdateMessage(message);
                return ResponseService.CreateOkResponse(result.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }
        public List<User> GetLatestUsers(string username)
        {
            return iMessageDAL.GetLatestUsers(username); 
        }
    }
}
