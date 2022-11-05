using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IImageDAL
    {
        public List<Image> GetImages(int PostID); 
        public Image GetImage(int ImageID);
        public bool AddImage(Image image);
        public bool DeleteImage(int ImageID);

    }
}
