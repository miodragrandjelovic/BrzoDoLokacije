using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class MessageDAL : IMessageDAL
    {
        private readonly Database _context;

        public MessageDAL(Database context)
        {
            _context = context;
        }

        public bool AddMessage(Message message)
        {
            if (message == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.Messages.Add(message);
            _context.SaveChanges(); 

            return true;
        }

        public bool DeleteMessage(int messageId)
        {
            Message message = GetMessage(messageId);
            if (message == null)
                throw new Exception(Constants.Constants.resNotFoundMessage);

            _context.Remove(message);
            _context.SaveChanges();
            return true; 
        }

        public Message GetMessage(int messageId)
        {
            Message message = _context.Messages.Where(message => (message.Id == messageId)).FirstOrDefault();
            return message; 
        }

        public List<Message> GetMessages(string usernameSender, string usernameReceiver)
        {
            List<Message> messages = _context.Messages.Where(message => message.Sender.Username.Equals(usernameSender)
                                    && message.Receiver.Username.Equals(usernameReceiver))
                                    .OrderBy(message => message.Time)
                                    .ToList();
            return messages; 
        }

        public bool UpdateMessage(Message message)
        {
            if (message == null)
                throw new Exception(Constants.Constants.resNullValue);
            _context.Update(message);
            _context.SaveChanges();

            return true; 
        }
    }
}
