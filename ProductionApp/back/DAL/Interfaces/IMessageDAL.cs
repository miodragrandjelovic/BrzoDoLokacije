using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IMessageDAL
    {
        public Message GetMessage(int messageId); 
        public Boolean AddMessage(Message message);
        public Boolean DeleteMessage(int messageId);
        public Boolean UpdateMessage(Message message);

        public List<Message> GetMessages(string usernameSender, string usernameReceiver); 
    }
}
