using PyxisKapriBack.Services.Interfaces;
using System.Drawing;
namespace PyxisKapriBack.Services
{
    public class FileService : IFileService
    {

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
            var path = Path.Combine(Directory.GetCurrentDirectory(), Constants.Constants.ROOT_FOLDER, "DefaultProfileImage");
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
            var folderPath = Path.Combine(Directory.GetCurrentDirectory(), folderName); 
            DirectoryInfo info = Directory.CreateDirectory(folderPath);
            if(info.Exists)
                return true;

            return false;

        }
        // umesto topFolder ide putanja iz baze
        public string AddFile(string path, IFormFile file)
        {
            var sections = file.FileName.Split('.');
            string filePath;
            if (sections[1].Equals("avif")) // avif je novi format pa mora da se konvertuje zato sto nece da ga prikaze lepo
                filePath = Path.Combine(path,sections[0]+".jpg");
            else
                filePath = Path.Combine(path, file.FileName);

            using (FileStream fs = new FileStream(filePath, FileMode.Create))
            {
                file.CopyTo(fs);
            }

            return filePath;
            
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

        public bool UpdateFile(string path,string fileName ,IFormFile file, out string newFileName)
        {
            bool succeed = false;
            var imagePath = Path.Combine(Directory.GetCurrentDirectory(), path, fileName);
            newFileName = file.FileName;

            if (File.Exists(imagePath))
            {
                File.Delete(imagePath);
            }

            AddFile(path, file);
            succeed = true;

            return succeed;
          
        }

        public string GetDefaultPath()
        {
            var path = Path.Combine(Constants.Constants.ROOT_FOLDER, Constants.Constants.DEFAULT_IMAGE_PATH);
            return path;
        }

        public string GetProfileImagePath(string folderName)
        {
            var path = Path.Combine(Constants.Constants.ROOT_FOLDER, folderName);
            return path;
        }

        public bool CheckIfProfileImageExists(string path, string fileName)
        {
            var fullPath = Path.Combine(Directory.GetCurrentDirectory(),path, fileName);
            if (File.Exists(fullPath))
                return true;
            return false;
        }

        public void CreateUserFolder(string folderName)
        {
            if (!Directory.Exists(Path.Combine(Directory.GetCurrentDirectory(), folderName)))
            {
                var path = Path.Combine(Constants.Constants.ROOT_FOLDER,folderName);
                CreateFolder(path);
                var defaultImagePath = Path.Combine(Directory.GetCurrentDirectory(), Constants.Constants.ROOT_FOLDER, Constants.Constants.DEFAULT_IMAGE_PATH);
                var sourcePath = Directory.GetFiles(defaultImagePath, Constants.Constants.DEFAULT_IMAGE_NAME).FirstOrDefault();
                var destPath = Path.Combine(GetProfileImagePath(folderName), Constants.Constants.DEFAULT_IMAGE_NAME);
                File.Copy(sourcePath, destPath);
            }
        }

        public string GetPostName()
        {
            return Constants.Constants.POST_NAME + _generator.NextInt64();
        }
        
    }
}
