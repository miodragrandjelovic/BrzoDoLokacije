using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class MessageUI : IMessageUI
    {
        private IMessageService messageService;
        private IUserService userService; 

        public MessageUI(IMessageService messageService, IUserService userService)
        {
            this.messageService = messageService;
            this.userService = userService;
        }
        public Response AddMessage(MessageDTO message)
        {
            User sender = userService.GetUser(message.UsernameSender); 
            User receiver = userService.GetUser(message.UsernameReceiver);
            Message newMessage = new Message
            {
                Text = message.Text,
                Sender = sender,
                Receiver = receiver,
                SenderId = sender.Id,
                ReceiverId = receiver.Id, 
                Time = DateTime.Now
            };

            return messageService.AddMessage(newMessage); 
        }

        public Response DeleteMessage(int messageId)
        {
            return messageService.DeleteMessage(messageId);
        }

        public MessageDTO GetMessage(int messageId)
        {
            Message message = messageService.GetMessage(messageId);

            if (message == null)
                return null; 

            MessageDTO messageDTO = new MessageDTO { Id = message.Id, 
                                                     Text = message.Text, 
                                                     Time = message.Time,
                                                     UsernameSender = message.Sender.Username,
                                                     UsernameReceiver = message.Receiver.Username};

            return messageDTO; 
        }

        public List<MessageDTO> GetMessages(string usernameReceiver)
        {
            List<MessageDTO> messagesDTO = new List<MessageDTO>();
            String usernameSender = userService.GetLoggedUser(); 
            List<Message> messages = messageService.GetMessages(usernameSender, usernameReceiver);

            foreach(var message in messages)
            {
                MessageDTO messageDTO = new MessageDTO
                {
                    Id = message.Id,
                    Text = message.Text,
                    Time = message.Time,
                    UsernameSender = message.Sender.Username,
                    UsernameReceiver = message.Receiver.Username
                };
                messagesDTO.Add(messageDTO);
            }

            return messagesDTO;
        }

        public Response UpdateMessage(MessageDTO message)
        {
            User sender = userService.GetUser(message.UsernameSender);
            User receiver = userService.GetUser(message.UsernameReceiver);

            Message updatedMessage = messageService.GetMessage((int)message.Id);

            updatedMessage.Text = message.Text; 
            
            return messageService.UpdateMessage(updatedMessage); 
        }

    }
}
