namespace PyxisKapriBack.Models
{
    public class Connection
    {
        public string ConnectionId { get; set; }    
        public int UserId { get; set; }
        public string UserName { get; set; }
        public User User { get; set; }

    }
}
