using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Receipt
    {
        public Receipt()
        {
            ReceiptDetails = new HashSet<ReceiptDetail>();
        }

        public int Id { get; set; }
        public int Total { get; set; }
        public DateTime CreateDate { get; set; }
        public string Note { get; set; }
        public string IsAccepted { get; set; }
        public int IdRestaurant { get; set; }
        public string PhoneClient { get; set; }
        public string IdVouncher { get; set; }

        public virtual Restaurant IdRestaurantNavigation { get; set; }
        public virtual Vouncher IdVouncherNavigation { get; set; }
        public virtual Client PhoneClientNavigation { get; set; }
        public virtual ICollection<ReceiptDetail> ReceiptDetails { get; set; }
    }
}
