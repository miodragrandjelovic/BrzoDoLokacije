using Microsoft.AspNetCore.SignalR;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using System.Web;
namespace PyxisKapriBack.Chat
{
    public class ChatHub : Hub
    {
        private readonly IUserService userService;
        private readonly IMessageService messageService;
        private readonly HttpContext httpContext;
        public ChatHub(IUserService userService, IMessageService messageService)
        {
            this.userService = userService;
            this.messageService = messageService;
            httpContext = Context.GetHttpContext();
        }

        public override Task OnConnectedAsync()
        {
            
            var loggedUser = userService.GetUser(userService.GetLoggedUser());

            var toUser = userService.GetUser(httpContext.Request.Headers["toUsername"]);
            if (loggedUser == null)
                return base.OnDisconnectedAsync(new Exception("User not found"));

            userService.AddNewConnection(new Connection
            {
                ConnectionId = Context.ConnectionId,
                SenderId = loggedUser.Id,
                Sender = loggedUser,
                ReceiverId = toUser.Id,
                Receiver = toUser
                
            });
            return base.OnConnectedAsync();
        }
        public override Task OnDisconnectedAsync(Exception? exception)
        {
            return base.OnDisconnectedAsync(exception);
        }
        /*
        public async Task SendPrivateMessage(string reciever, string connectionId, string message)
        {
            var sendUser = userService.GetUser(userService.GetLoggedUser());
            var recieveUser = userService.GetUser(reciever);

            if (sendUser == null || recieveUser == null)
                return;

            // proveriti da li konekcija vec postoji
            // ako postoji sacuvati poruku u bazi sa tom konekcijom
            // ako ne postoji napraviti konekciju izmedju dva korisnika i sacuvati poruku

            await Clients.Client(connectionId).SendAsync("RecieveMessage", recieveUser, message);
        }
        public void Connect(string fromUserName, string toUserName)
        {
            var id = Context.ConnectionId;
            var userConnections = userService.GetUserConnections(fromUserName);
            var loggedUser = userService.GetUser(userService.GetLoggedUser());
            var reciver = userService.GetUser(toUserName);
            if(userConnections.Count(c => c.ConnectionId.Equals(id)) == 0)
            {
                bool succed = userService.AddNewConnection(new Connection
                {
                    ConnectionId = id,
                    UserId = reciver.Id,
                    UserName = fromUserName,
                    User = loggedUser

                });
            }
            Connection currentUser = userService.GetConnectionById(id);

            Clients.Caller.SendAsync("",reciver);
        }
        */

    }
}
