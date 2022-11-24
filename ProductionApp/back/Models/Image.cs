namespace PyxisKapriBack.Models
{
    public class Image
    {
        [Key]
        public int Id { get; set; }
        public string ImageName { get; set; } = string.Empty;

        #region 'Post'
        public int PostId { get; set; }
        public Post Post { get; set; }
        #endregion
    }
}
