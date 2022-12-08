namespace PyxisKapriBack.DTOComponents
{
    public class MessageDTO
    {
        public long Id { get; set; } 
        public string Text { get; set; }
        public string UsernameSender { get; set; }
        public string UsernameReceiver { get; set; }
        public DateTime Time { get; set; }
    }
}
