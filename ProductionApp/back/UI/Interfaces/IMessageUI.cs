using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IMessageUI
    {
        public Response UpdateMessage(MessageDTO message); 
        public Response DeleteMessage(int messageId);
        public MessageDTO GetMessage(int messageId);
        public Response AddMessage(MessageDTO message);

        public List<MessageDTO> GetMessages(string usernamReceiver);
        public List<UserShortDTO> GetLatestUsers(); 
    }
}
