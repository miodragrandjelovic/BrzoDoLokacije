namespace PyxisKapriBack.Models
{
    public class Connection
    {
        public string ConnectionId { get; set; }    
        public int ReceiverId { get; set; } 
        public User Receiver { get; set; }
        public int SenderId { get; set; } 
        public User Sender { get; set; }

    }
}
