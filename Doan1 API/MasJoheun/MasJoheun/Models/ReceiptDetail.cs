using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class ReceiptDetail
    {
        public int Id { get; set; }
        public int IdReceipt { get; set; }

        public virtual Food IdNavigation { get; set; }
        public virtual Receipt IdReceiptNavigation { get; set; }
    }
}
