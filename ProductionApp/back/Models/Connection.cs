namespace PyxisKapriBack.Models
{
    public class Connection
    {
        public string ConnectionId { get; set; }    
        public int SenderId { get; set; }
        public int RecieverId { get; set; }
        public List<Message> Messages { get; set; } 

    }
}
