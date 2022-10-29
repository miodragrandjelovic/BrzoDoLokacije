namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IImageDAL
    {
        public List<Image> GetImages(int PostID); 
        public Image GetImage(int ImageID);
        public void AddImage(Image image);
        public void DeleteImage(int ImageID);

    }
}
