using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models; 
namespace PyxisKapriBack.DAL
{
    public class ImageDAL : IImageDAL
    {
        private Database _context; 
        public ImageDAL(Database context)
        {
            _context = context;
        }
        public bool AddImage(Image image)
        {
            if (image == null)
                return false; 
            _context.Images.Add(image);
            _context.SaveChanges();
            return true;
        }

        public bool DeleteImage(int ImageID)
        {
            Image image = GetImage(ImageID);
            if (image == null)
                return false; 

             _context.Remove(image);
             _context.SaveChanges();
            return true; 
        }

        public Image GetImage(int ImageID)
        {
            return _context.Images.Where(image => image.Id == ImageID).FirstOrDefault(); 
        }

        public List<Image> GetImages(int PostID)
        {
            return _context.Images.Where(image => image.PostId == PostID).ToList(); 
        }
    }
}
