using Microsoft.AspNetCore.SignalR;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Chat
{
    public class ChatHub : Hub
    {
        private readonly IUserService userService;
        private readonly IMessageService messageService;

        public ChatHub(IUserService userService, IMessageService messageService)
        {
            this.userService = userService;
            this.messageService = messageService;
        }

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
        public string GetConnectionId() => Context.ConnectionId;
    }
}
