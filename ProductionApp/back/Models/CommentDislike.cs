namespace PyxisKapriBack.Models
{
    public class CommentDislike
    {
        public int Id { get; set; }

        #region 'User'
        public int UserId { get; set; }
        public User User { get; set; }
        #endregion

        #region 'Comment'
        public int CommentId { get; set; }
        public Comment Comment { get; set; }
        #endregion
    }
}
