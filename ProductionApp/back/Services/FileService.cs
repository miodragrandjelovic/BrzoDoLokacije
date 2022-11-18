using PyxisKapriBack.Services.Interfaces;
using System.Drawing;
namespace PyxisKapriBack.Services
{
    public class FileService : IFileService
    {
        private const string ROOT_FOLDER = "Images";
        public byte[] ImageToByteArray(Image image)
        {
            MemoryStream ms = new MemoryStream();
            image.Save(ms, image.RawFormat);
            return ms.ToArray();
        }
        public Image ByteArrayToImage(byte[] byteArray)
        {
            MemoryStream ms = new MemoryStream(byteArray, 0, byteArray.Length);
            ms.Write(byteArray, 0, byteArray.Length);
            Image image = Image.FromStream(ms, true);

            return image;
        }

        public string GetFileName(string imagePath)
        {
            var sections = imagePath.Split('/');

            return sections.Last();
        }

        public string ConvertStringToBase64(string str)
        {
            var byteArray = System.Text.Encoding.UTF8.GetBytes(str);

            return Convert.ToBase64String(byteArray);
        }

        public string GetDefaultProfileImage()
        {
            Console.WriteLine(Directory.GetCurrentDirectory());
            return Directory.GetFiles(Directory.GetCurrentDirectory() + "" + Constants.Constants.IMAGE_PATH).FirstOrDefault();
        }

        public byte[]? ConvertImageToByte(string image)
        {
            return System.IO.File.ReadAllBytes(image);
        }

        public bool CreateFolder(string folderName,out string folderPath)
        {
            folderPath = Path.Combine(Directory.GetCurrentDirectory(), ROOT_FOLDER, folderName);

            Directory.CreateDirectory(folderPath);
            return true;

        }
        // umesto topFolder ide putanja iz baze
        public void AddFile(string topFolder, IFormFile file)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(), ROOT_FOLDER, topFolder,"profileImage.jpg");

            using(FileStream fs = new FileStream(path, FileMode.Create))
            {
                file.CopyTo(fs);
            }

            
        }

        public bool CheckIfFolderExists(string folderPath)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(), ROOT_FOLDER, folderPath);
            if (Directory.Exists(path))
                return true;

            return false;
        }

        public IFormFile GetFile(string topFolder, string fileName)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(),ROOT_FOLDER,topFolder, fileName);
            if (File.Exists(path))
            {
                using (var stream = File.OpenRead(path))
                {
                    var file = new FormFile(stream, 0, stream.Length, "image", Path.GetFileName(stream.Name))
                    {
                        Headers = new HeaderDictionary(),
                        ContentType = "image/jpeg"
                    };
                    return file;
                }
                
            }
            return null;
        }
    }
}
