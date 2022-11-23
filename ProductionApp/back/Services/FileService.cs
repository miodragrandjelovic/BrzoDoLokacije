using PyxisKapriBack.Services.Interfaces;
using System.Drawing;
namespace PyxisKapriBack.Services
{
    public class FileService : IFileService
    {
        private const string ROOT_FOLDER = "Images";
        private static Random _generator = new Random();
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

        public string? GetDefaultProfileImage()
        {
            Console.WriteLine(Directory.GetCurrentDirectory());
            var path = Path.Combine(Directory.GetCurrentDirectory(), ROOT_FOLDER, "DefaultProfileImage");
            var image = Directory.GetFiles(path).FirstOrDefault();
            if(!string.IsNullOrEmpty(image))
                return image;

            return string.Empty;

        }



        public byte[]? ConvertImageToByte(string image)
        {
            return System.IO.File.ReadAllBytes(image);
        }

        public bool CreateFolder(string folderName)
        {
            var folderPath = Path.Combine(Directory.GetCurrentDirectory(), ROOT_FOLDER, folderName);

            Directory.CreateDirectory(folderPath);
            return true;

        }
        // umesto topFolder ide putanja iz baze
        public void AddFile(string path, IFormFile file)
        {
            var extension = GetExtension(file.FileName);
            var imageName = "profileImage" + "." + extension;
            var filePath = Path.Combine(path,imageName);

            using(FileStream fs = new FileStream(filePath, FileMode.Create))
            {
                file.CopyTo(fs);
            }

            
        }

        public bool CheckIfFolderExists(string path)
        {
            if (Directory.Exists(path))
                return true;
            return false;
        }

        public IFormFile GetFile(string path, string fileName)
        {
            var filePath = Path.Combine(path, fileName);
            if (File.Exists(filePath))
            {
                using (var stream = File.OpenRead(filePath))
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

        public byte[] GetUserProfileImage(string path)
        {
            var profileImagePath = Directory.GetFiles(path,"profileImage.jpg").FirstOrDefault();
            if(profileImagePath != null)
            {
                return ConvertImageToByte(profileImagePath);
            }
            return ConvertImageToByte(GetDefaultProfileImage());
        }

        public bool UpdateFile(string topFolder, IFormFile file)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(), topFolder, "profileImage.jpg");

            if (CheckIfFolderExists(path))
            {
                Directory.Move(path,path);//izmeni
                return true;
            }
            else
            {
                AddFile(topFolder,file);
                return true;
            }

            return false;
        }

        public string GetDefaultPath(string folderName)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory (), ROOT_FOLDER,folderName);
            return path;
        }

        public string GetExtension(string image)
        {
            return image.Split('.').Last();
        }
    }
}
