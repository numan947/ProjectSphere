import { Center, Text } from "@chakra-ui/react";
import { Navigate } from "react-router-dom";
import useAuthStore from "../domain/auth/AuthStore";

function LandingPage() {
  const { token } = useAuthStore();
  if (token) {
    return <Navigate to="/home" />;
  }

  return (
    <Center>
      <Text>Hello World</Text>
    </Center>
  );
}

export default LandingPage;
