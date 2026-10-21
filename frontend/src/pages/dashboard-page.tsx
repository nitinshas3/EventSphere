import { useRoles } from "@/hooks/use-roles";
import { useEffect } from "react";
import { useNavigate } from "react-router";

// Smart redirector: waits for roles to load, then sends each role to the right dashboard page
const DashboardPage: React.FC = () => {
  const { isLoading, isOrganizer, isStaff } = useRoles();
  const navigate = useNavigate();

  useEffect(() => {
    // Do NOT redirect until roles have been decoded from the JWT
    if (isLoading) return;

    if (isOrganizer) {
      navigate("/dashboard/events", { replace: true });
    } else if (isStaff) {
      navigate("/dashboard/validate-qr", { replace: true });
    } else {
      // Default role (Attendee) → show their purchased tickets
      navigate("/dashboard/tickets", { replace: true });
    }
  }, [isLoading, isOrganizer, isStaff, navigate]);

  return <p>Loading...</p>;
};

export default DashboardPage;
