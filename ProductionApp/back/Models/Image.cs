namespace PyxisKapriBack.Models
{
    public class Image
    {
        [Key]
        public int Id { get; set; }
        public byte[] ImageData { get; set; }

        #region 'Post'
        public int PostId { get; set; }
        public Post Post { get; set; }
        #endregion
    }
}
