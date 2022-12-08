namespace PyxisKapriBack.Models
{
    public class View
    {
        [Key]
        public int Id { get; set; }

        #region 'Post'
        public int PostId { get; set; }
        public Post Post { get; set; }
        #endregion

        #region 'User'
        public int UserId { get; set; }
        public User User { get; set; }
        #endregion
    }
}
