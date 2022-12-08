using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.JWTManager.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class AuthService : IAuthService
    {
        private readonly IUserService userService;
        private readonly IEncryptionManager encryptionManager;
        private readonly IJWTManagerRepository JWTManager;
        private readonly IFileService fileService;

        public AuthService(
            IUserService userService, 
            IEncryptionManager encryptionManager, 
            IJWTManagerRepository JWTManager,
            IFileService fileService
            )
        {
            this.userService = userService;
            this.encryptionManager = encryptionManager;
            this.JWTManager = JWTManager;
            this.fileService = fileService;
            this.fileService = fileService;
        }


        public async Task<Response> Login(LoginDTO request)
        {
            var response = new Response();  
            var user = userService.GetUser(request.UsernameOrEmail);
            if (user == null)
            {
                response.StatusCode = StatusCodes.Status404NotFound;
                response.Message = "User doesn't exist!";
                return response;
            }

            if (!encryptionManager.DecryptPassword(request.Password, user.Password, user.PasswordKey))
            {
                response.StatusCode= StatusCodes.Status403Forbidden;
                response.Message = "Wrong password!";
                return response;
            }


            var tokenString = JWTManager.GenerateToken(user);
            response.StatusCode = StatusCodes.Status200OK;
            response.Message = tokenString;
            return response;
            
        }

        public async Task<Response> Register(RegisterDTO request)
        {
            var response = new Response();
            byte[] passwordHash, passwordKey;
            if (await userService.UserAlreadyExists(request.Username))
            {
                response.StatusCode = StatusCodes.Status400BadRequest;
                response.Message = "User already exists";
                return response;
            }


            //var convertedImage = FileService.ConvertImageToByte(FileService.GetDefaultProfileImage());

            fileService.CreateUserFolder(request.Username);  
            var folderPath = fileService.GetProfileImagePath(request.Username);
            var fileName = Constants.Constants.DEFAULT_IMAGE_NAME;

            encryptionManager.EncryptPassword(request.Password, out passwordHash, out passwordKey);
            User newUser = new User
            {
                //ProfileImage = convertedImage,
                FolderPath = folderPath,
                FileName = fileName,
                Username = request.Username,
                FirstName = request.FirstName,
                LastName = request.LastName,
                Password = passwordHash,
                PasswordKey = passwordKey,
                Email = request.Email,
                Role = userService.GetUserRole(),
                CountryId = Constants.ModelConstants.DEFAULT

            };
            var answer = userService.AddNewUser(newUser);
            if (answer.StatusCode.Equals(StatusCodes.Status200OK))
            {
                
                return new Response
                {
                    StatusCode = StatusCodes.Status200OK,
                    Message = "User added successfully!"
                };
            }


            return new Response
            {
                StatusCode = StatusCodes.Status400BadRequest,
                Message = "Error while adding user!"
            };
        }
    }
}
