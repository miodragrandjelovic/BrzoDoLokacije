﻿using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class ImageDAL : IImageDAL
    {
        private Database _context; 
        public ImageDAL(Database context)
        {
            _context = context;
        }
        public void AddImage(Image image)
        {
            _context.Images.Add(image);
            _context.SaveChanges();
        }

        public void DeleteImage(int ImageID)
        {
            Image image = GetImage(ImageID);
            if (image != null)
            {
                _context.Remove(image);
                _context.SaveChanges();
            }
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
