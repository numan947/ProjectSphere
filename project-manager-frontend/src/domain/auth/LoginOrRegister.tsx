import {
  Button,
  FormControl,
  FormErrorMessage,
  FormLabel,
  HStack,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  Alert,
  AlertIcon,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Text,
  Tabs,
} from "@chakra-ui/react";
import { useState } from "react";
import { z } from "zod";
import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import useLogin from "./hooks/useLogin";
import { useNavigate } from "react-router-dom";
import useRegister from "./hooks/useRegister";

const defaultValues = {
  email: "",
  password: "",
  firstName: "",
  lastName: "",
  confirmPassword: "",
};

const loginSchema = z.object({
  email: z.string().email("Invalid email").min(1, "Email is required"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

const registerSchema = z
  .object({
    firstName: z.string().min(1, "First name is required"),
    lastName: z.string().min(1, "Last name is required"),
    email: z.string().email("Invalid email").min(1, "Email is required"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z
      .string()
      .min(6, "Password must be at least 6 characters"),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: "custom",
        message: "The passwords did not match",
        path: ["confirmPassword"],
      });
    }
  });

interface LoginOrRegisterProps {
  isOpen: boolean;
  onClose: () => void;
}

const LoginOrRegister = ({ isOpen, onClose }: LoginOrRegisterProps) => {
  const navigate = useNavigate(); // for navigation

  // tabstate for switching between login and register
  const [tabIndex, setTabIndex] = useState(0); // 0 for login, 1 for register

  // error message state for displaying error message in the modal
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  // form handling for login and register
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(tabIndex == 0 ? loginSchema : registerSchema),
    defaultValues: defaultValues,
  });

  // close modal and reset form and cleanup
  const handleModalClose = () => {
    setErrorMessage(null);
    setTabIndex(0);
    reset();
    onClose();
  };

  // error handler for login and register
  const loginOrRegisterFailureHandler = (message: string) => {
    setErrorMessage(message);
    setTimeout(() => {
      setErrorMessage(null);
    }, 5000);
  };

  // success handler for login
  const loginSuccessHandler = () => {
    // close the modal
    handleModalClose();
    // redirect to home page
    navigate("/home");
  };

  const registerSuccessHandler = () => {
    // close the modal
    handleModalClose();
    // redirect to activate account page
    navigate("/activate-account");
  };

  // useLogin
  const { mutate: performLogin, isPending: loginLoading } = useLogin(
    loginSuccessHandler,
    loginOrRegisterFailureHandler
  );

  // useRegister
  const { mutate: performRegistration, isPending: registrationLoading } =
    useRegister(registerSuccessHandler, loginOrRegisterFailureHandler);

  return (
    <Modal
      isOpen={isOpen}
      onClose={handleModalClose}
      motionPreset="slideInTop"
      closeOnEsc={!registrationLoading && !loginLoading}
      closeOnOverlayClick={!registrationLoading && !loginLoading}
    >
      <ModalOverlay
        bg="blackAlpha.200"
        backdropFilter="blur(5px) hue-rotate(40deg)"
      />
      <ModalContent
        borderRadius="md" // Rounded corners
        boxShadow="lg" // Subtle shadow
      >
        <ModalHeader>{tabIndex == 0 ? "Login" : "Register"}</ModalHeader>
        <ModalCloseButton disabled={registrationLoading || loginLoading} />
        <ModalBody>
          {errorMessage && (
            <Alert mb={3} status="error">
              <AlertIcon />
              {errorMessage}
            </Alert>
          )}
          <Tabs index={tabIndex} onChange={setTabIndex} variant="soft-rounded">
            <TabList>
              <Tab
                onClick={() => {
                  setTabIndex(0);
                }}
              >
                Login
              </Tab>
              <Tab
                onClick={() => {
                  setTabIndex(1);
                }}
              >
                Register
              </Tab>
            </TabList>

            <TabPanels>
              <TabPanel>
                <form
                  onSubmit={handleSubmit((data) => {
                    performLogin(data);
                  })}
                >
                  <FormControl isInvalid={!!errors.email}>
                    <FormLabel>Email</FormLabel>
                    <Controller
                      name="email"
                      control={control}
                      render={({ field }) => (
                        <Input {...field} placeholder="Enter your email" />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.email && errors.email.message}
                    </FormErrorMessage>
                  </FormControl>
                  <FormControl mt={4} isInvalid={!!errors.password}>
                    <FormLabel>Password</FormLabel>
                    <Controller
                      name="password"
                      control={control}
                      render={({ field }) => (
                        <Input
                          {...field}
                          type="password"
                          placeholder="Enter your password"
                        />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.password && errors.password.message}
                    </FormErrorMessage>
                  </FormControl>
                  <HStack mt={4} pt={4} justifyContent="end">
                    <Button
                      type="submit"
                      colorScheme="green"
                      isLoading={loginLoading}
                    >
                      Login
                    </Button>
                    <Button
                      type="button"
                      variant="outline"
                      colorScheme="red"
                      onClick={() => {
                        handleModalClose();
                      }}
                      isLoading={loginLoading}
                    >
                      Cancel
                    </Button>
                  </HStack>
                </form>
              </TabPanel>

              <TabPanel>
                <form
                  onSubmit={handleSubmit((data) => {
                    performRegistration({
                      email: data.email,
                      password: data.password,
                      fname: data.firstName,
                      lname: data.lastName,
                    });
                  })}
                >
                  <FormControl isInvalid={!!errors.firstName}>
                    <FormLabel>First Name</FormLabel>
                    <Controller
                      name="firstName"
                      control={control}
                      render={({ field }) => (
                        <Input {...field} placeholder="Enter your first name" />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.firstName && errors.firstName.message}
                    </FormErrorMessage>
                  </FormControl>
                  <FormControl mt={4} isInvalid={!!errors.lastName}>
                    <FormLabel>Last Name</FormLabel>
                    <Controller
                      name="lastName"
                      control={control}
                      render={({ field }) => (
                        <Input {...field} placeholder="Enter your last name" />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.lastName && errors.lastName.message}
                    </FormErrorMessage>
                  </FormControl>

                  <FormControl isInvalid={!!errors.email} mt={4}>
                    <FormLabel>Email</FormLabel>
                    <Controller
                      name="email"
                      control={control}
                      render={({ field }) => (
                        <Input {...field} placeholder="Enter your email" />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.email && errors.email.message}
                    </FormErrorMessage>
                  </FormControl>

                  <FormControl mt={4} isInvalid={!!errors.password}>
                    <FormLabel>Password</FormLabel>
                    <Controller
                      name="password"
                      control={control}
                      render={({ field }) => (
                        <Input
                          {...field}
                          type="password"
                          placeholder="Enter your password"
                        />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.password && errors.password.message}
                    </FormErrorMessage>
                  </FormControl>

                  <FormControl mt={4} isInvalid={!!errors.confirmPassword}>
                    <FormLabel>Confirm Password</FormLabel>
                    <Controller
                      name="confirmPassword"
                      control={control}
                      render={({ field }) => (
                        <Input
                          {...field}
                          type="password"
                          placeholder="Confirm Password"
                        />
                      )}
                    />
                    <FormErrorMessage>
                      {errors.confirmPassword && errors.confirmPassword.message}
                    </FormErrorMessage>
                  </FormControl>
                  <HStack mt={4} pt={4} justifyContent="end">
                    <Button
                      type="submit"
                      colorScheme="green"
                      isLoading={registrationLoading}
                    >
                      Register
                    </Button>
                    <Button
                      type="button"
                      variant="outline"
                      colorScheme="red"
                      onClick={() => {
                        handleModalClose();
                      }}
                      isLoading={registrationLoading}
                    >
                      Cancel
                    </Button>
                  </HStack>
                </form>
              </TabPanel>
            </TabPanels>
          </Tabs>
        </ModalBody>
        <ModalFooter>
          {tabIndex == 0 ? (
            <Text>
              Don't have an account?{" "}
              <Button
                variant="link"
                colorScheme="blue"
                onClick={() => {
                  setTabIndex(1);
                }}
              >
                Register
              </Button>
            </Text>
          ) : (
            <Text>
              Already have an account?{" "}
              <Button
                variant="link"
                colorScheme="blue"
                onClick={() => {
                  setTabIndex(0);
                }}
              >
                Login
              </Button>
            </Text>
          )}
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

export default LoginOrRegister;
