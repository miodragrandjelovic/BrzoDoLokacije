using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IMessageService
    {
        public Response AddMessage(Message message);
        public Response DeleteMessage(int messageId);
        public Response UpdateMessage(Message message);
        public Message GetMessage(int messageId);
        public List<Message> GetMessages(string usernameSender, string usernameReceiver);
    }
}
