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

            if (message == null)
                throw new Exception(Constants.Constants.resNullValue);

            if (message.Sender == null)
                throw new Exception(Constants.Constants.resNotFoundSender);

            if (message.Receiver == null)
                throw new Exception(Constants.Constants.resNotFoundReceiver);

            if (message.Text == null)
                throw new Exception(Constants.Constants.resEmptyTextMessage);

            if (message.Sender.Username.Equals(message.Receiver.Username))
                throw new Exception(Constants.Constants.resSameSenderAndReceiver); 
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

        public List<User> GetLatestUsers(string username)
        {
            var pairs = _context.Messages.Where(message => message.Sender.Username.Equals(username)
                                            || (message.Receiver.Username.Equals(username)))
                                    .Include(message => message.Sender)
                                    .Include(message => message.Receiver)
                                    .OrderBy(message => message.Time)
                                    .Select(message => new { Receiver = message.Receiver, Sender = message.Sender })
                                    .ToList();
            var users = new List<User>(); 
            foreach(var pair in pairs)
            {
                if(pair.Sender.Username.Equals(username))
                    users.Add(pair.Receiver);
                else 
                    users.Add(pair.Sender);

            }
            return users.Distinct().ToList();
         }

        public Message GetMessage(int messageId)
        {
            Message message = _context.Messages.Where(message => (message.Id == messageId))
                                               .Include(message => message.Sender)
                                               .Include(message => message.Receiver)
                                               .FirstOrDefault();
            return message; 
        }

        public List<Message> GetMessages(string usernameSender, string usernameReceiver)
        {
            List<Message> messages = _context.Messages.Where(message => message.Sender.Username.Equals(usernameSender)
                                    && message.Receiver.Username.Equals(usernameReceiver))
                                    .Include(message => message.Sender)
                                    .Include(message => message.Receiver)
                                    .OrderByDescending(message => message.Time)
                                    .ToList();
            return messages; 
        }

        public bool UpdateMessage(Message message)
        {
            if (message == null)
                throw new Exception(Constants.Constants.resNullValue);

            if (message.Sender == null)
                throw new Exception(Constants.Constants.resNotFoundSender);

            if (message.Receiver == null)
                throw new Exception(Constants.Constants.resNotFoundReceiver); 

            _context.Update(message);
            _context.SaveChanges();

            return true; 
        }
    }
}
