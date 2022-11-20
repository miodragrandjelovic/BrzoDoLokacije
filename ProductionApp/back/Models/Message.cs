namespace PyxisKapriBack.Models
{
    public class Message
    {
        [Key]
        public long Id { get; set; }
        public String Text { get; set; }
        public DateTime Time { get; set; }

        #region 'Sender'
        public int SenderId { get; set; }
        public User Sender { get; set; }
        #endregion

        #region 'Receiver'
        public int ReceiverId { get; set; }
        public User Receiver { get; set; }
        #endregion
    }
}
