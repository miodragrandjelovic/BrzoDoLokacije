namespace PyxisKapriBack.Models
{
    public class Like
    {
        public int Id { get; set; }

        #region 'User'
        public int UserId { get; set; } 
        public User User { get; set; }
        #endregion

        #region 'Post'
        public int PostId { get; set; }
        public Post Post{ get; set; }
        #endregion
    }
}
