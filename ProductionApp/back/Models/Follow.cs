namespace PyxisKapriBack.Models
{
    public class Follow
    {
        [Key]
        public int Id { get; set; }

        // ko je zapratio -> ja
        #region 'Follower'

        [ForeignKey("Follower")]
        public int FollowerId { get; set; }

        public User Follower { get; set; }

        #endregion

        // ko je zapracen -> Jaffa Crvenka 
        #region
        [ForeignKey("Following")]
        public int FollowingId { get; set; }

        public User Following { get; set; }

        #endregion
    }
}
