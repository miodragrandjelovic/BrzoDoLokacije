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
                ReceiverId = receiver.Id
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

        public List<MessageDTO> GetMessages(string usernameSender, string usernameReceiver)
        {
            List<MessageDTO> messagesDTO = new List<MessageDTO>();
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

            if (sender == null)
                return ResponseService.CreateErrorResponse(Constants.Constants.resNotFoundSender);

            if (receiver == null)
                return ResponseService.CreateErrorResponse(Constants.Constants.resNotFoundReceiver);
            
            Message updatedMessage = new Message
            {
                Id = message.Id,
                Text = message.Text,
                Sender = sender,
                SenderId = sender.Id,                
                Receiver = receiver,
                ReceiverId = receiver.Id
            };
            return messageService.UpdateMessage(updatedMessage); 
        }

    }
}
